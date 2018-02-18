package com.k41d.cms.business.domain.tag;


import java.time.LocalDateTime;

import lombok.Data;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;

@Data public class TagDTO implements LeylineDTO {

    private long id;

    private String name;

    private LocalDateTime createdAt;


}
