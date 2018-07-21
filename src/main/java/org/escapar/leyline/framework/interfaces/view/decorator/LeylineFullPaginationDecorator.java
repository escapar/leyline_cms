package org.escapar.leyline.framework.interfaces.view.decorator;

import com.google.auto.service.AutoService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dialect.springdata.Keys;
import org.thymeleaf.dialect.springdata.decorator.PaginationDecorator;
import org.thymeleaf.dialect.springdata.util.Messages;
import org.thymeleaf.dialect.springdata.util.PageUtils;
import org.thymeleaf.dialect.springdata.util.Strings;
import org.thymeleaf.model.IProcessableElementTag;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.thymeleaf.dialect.springdata.util.Strings.*;

/**
 * Created by bytenoob on 6/9/16.
 */

@Component
@AutoService(PaginationDecorator.class)
public  class LeylineFullPaginationDecorator implements PaginationDecorator {
    private static  String DEFAULT_CLASS = "pagination";
    private static  String BUNDLE_NAME = LeylineFullPaginationDecorator.class.getSimpleName();
    private static  int DEFAULT_PAGE_SPLIT = 7;
    private static  String SLASH = "/";
    private static  String HTML = ".html";

    private static String createPageUrl( ITemplateContext context, int pageNumber) {
        String prefix = getParamPrefix(context);
         Collection<String> excludedParams = Arrays.asList(new String[]{prefix.concat(PAGE)});
         String baseUrl = buildBaseUrl(context, excludedParams).replaceAll("(\\/page\\/\\d*)+(\\.html|.)", "");

        return buildUrl(baseUrl, pageNumber, context).toString();
    }

    private static StringBuilder pageNumURLBuilder(int pageNumber) {
        return new StringBuilder(SLASH).append(PAGE).append(SLASH).append(pageNumber).append(HTML);
    }

    private static String getParamPrefix(ITemplateContext context) {
        String prefix = (String) context.getVariable(Keys.PAGINATION_QUALIFIER_PREFIX);

        return prefix == null ? EMPTY : prefix.concat("_");
    }

    private static String buildBaseUrl( ITemplateContext context, Collection<String> excludeParams) {
        //URL defined with pagination-url tag
         String url = (String) context.getVariable(Keys.PAGINATION_URL_KEY);

        if (url == null && context instanceof IWebContext) {
            //Creates url from actual request URI and parameters
             StringBuilder builder = new StringBuilder();
             IWebContext webContext = (IWebContext) context;
             HttpServletRequest request = webContext.getRequest();

            //URL base path from request
            builder.append(request.getRequestURI());

            Map<String, String[]> params = request.getParameterMap();
            Set<Map.Entry<String, String[]>> entries = params.entrySet();
            boolean firstParam = true;
            for (Map.Entry<String, String[]> param : entries) {
                //Append params not excluded to basePath
                String name = param.getKey();
                if (!excludeParams.contains(name)) {
                    if (firstParam) {
                        builder.append(Q_MARK);
                        firstParam = false;
                    } else {
                        builder.append(AND);
                    }

                    //Iterate over all values to create multiple values for parameter
                    String[] values = param.getValue();
                    Collection<String> paramValues = Arrays.asList(values);
                    Iterator<String> it = paramValues.iterator();
                    while (it.hasNext()) {
                        String value = it.next();
                        builder.append(name).append(EQ).append(value);
                        if (it.hasNext()) {
                            builder.append(AND);
                        }
                    }
                }
            }

            return builder.toString();
        }

        return url == null ? EMPTY : url;
    }

    private static StringBuilder buildUrl(String baseUrl, int pageNumber, ITemplateContext context) {
        String paramAppender = String.valueOf(baseUrl).contains(Q_MARK) ? AND : "";
        String prefix = getParamPrefix(context);

        return new StringBuilder(baseUrl).append(pageNumURLBuilder(pageNumber)).append(paramAppender).append(prefix);
    }

    public String getIdentifier() {
        return "full-static";
    }

    public String decorate(final IProcessableElementTag tag, final ITemplateContext context) {

        Page<?> page = PageUtils.findPage(context);

        //laquo
        String firstPage = createPageUrl(context, 0);
        boolean isFirstPage = page.getNumber() == 0;
        Locale locale = context.getLocale();
        String laquo = isFirstPage ? getLaquo(locale) : getLaquo(firstPage, locale);

        //Previous page
        String previous = getPreviousPageLink(page, context);

        //Links
        String pageLinks = createPageLinks(page, context);

        //Next page
        String next = getNextPageLink(page, context);

        //raquo
        boolean isLastPage = page.getTotalPages() == 0 || page.getNumber() == (page.getTotalPages() - 1);
        String lastPageNum = String.valueOf(page.getTotalPages());
        String lastPage = createPageUrl(context, page.getTotalPages() - 1);
        String raquo = isLastPage ? getRaquo(lastPageNum,locale) : getRaquo(lastPage, lastPageNum, locale);

        boolean isUl = Strings.UL.equalsIgnoreCase(tag.getElementCompleteName());
        String currentClass = tag.getAttributeValue(Strings.CLASS);
        String clas = (isUl && !Strings.isEmpty(currentClass)) ? currentClass : DEFAULT_CLASS;

        return Messages.getMessage(BUNDLE_NAME, "pagination", locale, clas, laquo, previous, pageLinks, next, raquo);
    }

    private String createPageLinks( Page<?> page,  ITemplateContext context) {
        if( page.getTotalElements()==0 ){
            return Strings.EMPTY;
        }

        int pageSplit = DEFAULT_PAGE_SPLIT;
        Object paramValue = context.getVariable(Keys.PAGINATION_SPLIT_KEY);
        if (paramValue != null) {
            pageSplit = (Integer) paramValue;
        }

        int firstPage = 0;
        int latestPage = page.getTotalPages();
        int currentPage = page.getNumber();
        if (latestPage >= pageSplit) {
            //Total pages > than split value, create links to split value
            int pageDiff = latestPage - currentPage;
            if (currentPage == 0) {
                //From first page to split value
                latestPage = pageSplit;
            } else if (pageDiff < pageSplit) {
                //From split value to latest page
                firstPage = currentPage - (pageSplit - pageDiff);
            } else {
                //From current page -1 to split value
                firstPage = currentPage - 1;
                latestPage = currentPage + pageSplit - 1;
            }
        }

        //Page links
        StringBuilder builder = new StringBuilder();
        for (int i = firstPage+1; i < latestPage-1; i++) {
            int pageNumber = i + 1;
            String link = createPageUrl(context, i);
            boolean isCurrentPage = i == currentPage;
            Locale locale = context.getLocale();
            String li = isCurrentPage ? getLink(pageNumber, locale) : getLink(pageNumber, link, locale);
            builder.append(li);
        }

        return builder.toString();
    }

    private String getLaquo(Locale locale) {
        return Messages.getMessage(BUNDLE_NAME, "laquo", locale);
    }

    private String getLaquo(String firstPage, Locale locale) {
        return Messages.getMessage(BUNDLE_NAME, "laquo.link", locale, firstPage);
    }

    private String getRaquo( String lastPageNum,Locale locale) {
        return Messages.getMessage(BUNDLE_NAME, "raquo", locale,lastPageNum);
    }

    private String getRaquo(String lastPageUrl, String lastPageNum, Locale locale) {
        return Messages.getMessage(BUNDLE_NAME, "raquo.link", locale, lastPageNum, lastPageUrl);
    }

    private String getLink(int pageNumber, Locale locale) {
        return Messages.getMessage(BUNDLE_NAME, "link.active", locale, pageNumber);
    }

    private String getLink(int pageNumber, String url, Locale locale) {
        return Messages.getMessage(BUNDLE_NAME, "link", locale, url, pageNumber);
    }

    private String getPreviousPageLink(Page<?> page,  ITemplateContext context) {
        int previousPage = page.getNumber() - 1;
        String msgKey = previousPage < 0 ? "previous.page" : "previous.page.link";
        Locale locale = context.getLocale();
        String link = createPageUrl(context, previousPage);

        return Messages.getMessage(BUNDLE_NAME, msgKey, locale, link);
    }

    private String getNextPageLink(Page<?> page,  ITemplateContext context) {
        int nextPage = page.getNumber() + 1;
        int totalPages = page.getTotalPages();
        String msgKey = nextPage == totalPages ? "next.page" : "next.page.link";
        Locale locale = context.getLocale();
        String link = createPageUrl(context, nextPage);

        return Messages.getMessage(BUNDLE_NAME, msgKey, locale, link);
    }

}
