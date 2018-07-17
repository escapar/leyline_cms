package org.escapar.leyline.framework.interfaces.dto.assembler;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.escapar.leyline.framework.domain.LeylineDO;
import org.escapar.leyline.framework.interfaces.dto.LeylineDTO;
import org.jodah.typetools.TypeResolver;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

/**
 * DTO转DO,特别注意的是无参构造仅能用于继承的子类.
 */
public class DTOAssembler<DO extends LeylineDO, DTO extends LeylineDTO> {
    public ModelMapper m = new ModelMapper();

    Type typeDO;
    Type typeDTO;

    public DTOAssembler() {
        Class<?>[] typeArgs = TypeResolver.resolveRawArguments(DTOAssembler.class, getClass());
        m.getConfiguration().setAmbiguityIgnored(true);
        typeDO = getType(typeArgs[0]);
        typeDTO = getType(typeArgs[1]);
    }

    public DTOAssembler(Type DO, Type DTO) {
        m.getConfiguration().setAmbiguityIgnored(true);
        typeDO = DO;
        typeDTO = DTO;
    }

    public DTOAssembler(Class<?> DO, Class<?> DTO) {
        m.getConfiguration().setAmbiguityIgnored(true);
        typeDO = getType(DO);
        typeDTO = getType(DTO);
    }

    private static Type getType(Class c) {
        return com.google.common.reflect.TypeToken.of(c).getType();
    }

    public DTO buildDTO(DO d) {
        return m.map(d, typeDTO);
    }

    public DO buildDO(DTO d) {
        return m.map(d, typeDO);
    }

    public List buildDOList(List<DTO> d) {
        return d.stream().map(e -> buildDO(e)).collect(Collectors.toList());
    }

    public List buildDTOList(List<DO> d) {
        return d.stream().map(e -> buildDTO(e)).collect(Collectors.toList());
    }

    public Page buildPageDTO(Page p) {
        return p.map(mapper);
    }

    Function<DO, DTO> mapper = (x) -> {
        Objects.requireNonNull(x);
        return buildDTO(x);
    };

}
