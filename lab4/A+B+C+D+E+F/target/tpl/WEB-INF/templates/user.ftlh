<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "common.ftl" as c/>

<@c.page>
    <#if handle??>
        <#assign userInfo = c.findBy(users, "handle", handle)!/>
    <#else>
        <#assign userInfo=c.findBy(users, "id", user_id)!/>
    </#if>
    <#assign viewedUser = userInfo[0]!/>
        <#if viewedUser.id?? && viewedUser.name?? && viewedUser.handle??>
            <div class="userPage">
                <@c.userlink viewedUser false/>

                <img class="userImage" src="/img/user.jpg">

                <ul class="userInfo">
                    <li>id: ${viewedUser.id}</li>
                    <li>handle: ${viewedUser.handle}</li>
                    <li>name: ${viewedUser.name}</li>
                    <li>posts: <a href="posts?user_id=${viewedUser.id}">${c.findAll(posts, "user_id", viewedUser.id)}</a></li>
                </ul>
            </div>
            <div class="moveUserBar">
                <@c.userArrow userInfo[1], "←"/>
                <@c.userArrow userInfo[2], "→"/>
            </div>
        <#else>
            <div class="userPage">
                <h1 class="noSuchUserMessage">No such user</h1>
            </div>
        </#if>
</@c.page>
