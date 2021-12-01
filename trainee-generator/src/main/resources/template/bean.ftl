<#-- 包名 -->
package ${packageName};

<#-- 必须的引用 -->
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
<#-- 可能存在的引用 -->
<#if importQuotes ?? && (importQuotes ? size > 0)>
<#list importQuotes as importQuote>
import ${importQuote};
</#list>
</#if>

/**
 * ${remarks}
 *
 * @author ${author}
 * @date ${date}
 */
@Getter
@Setter
@ToString
public class ${name} implements Serializable {

    private static final long serialVersionUID = 1L;

<#-- 字段 -->
<#if fields ?? && (fields ? size > 0)>
<#list fields as field>
    /**
     * ${field.remarks}
     */
    private ${field.type} ${field.name};
</#list>
</#if>
}