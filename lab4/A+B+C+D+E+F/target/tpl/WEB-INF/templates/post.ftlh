<#-- @ftlvariable name="post_id" type="java.lang.Long" -->

<#import "common.ftl" as c/>

<@c.page>
    <#if post_id??>
        <#assign post = c.findBy(posts, "id", post_id)/>

            <#if post?has_content>
                <@c.getPostBody post[0], false/>
            </#if>
    </#if>
</@c.page>