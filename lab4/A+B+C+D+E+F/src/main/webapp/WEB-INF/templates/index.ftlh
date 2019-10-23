<#import "common.ftl" as c/>

<@c.page>
<ul>
        <#list posts?reverse as post>
                <li style="list-style-type: none;"><@c.getPostBody post, true/></li>
        </#list>
</ul>
</@c.page>