<#-- @ftlvariable name="user_id" type="java.lang.Long" -->

<#import "common.ftl" as c/>

<@c.page>
    <#if user_id??>
        <#list posts as post>
            <#if post.user_id == user_id>
                <@c.getPostBody post, false/>
            </#if>
        </#list>
    </#if>
</@c.page>