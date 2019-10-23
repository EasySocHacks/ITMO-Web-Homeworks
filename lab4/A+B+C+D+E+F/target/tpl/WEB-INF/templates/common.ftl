<#macro header>
<header>
    <a href="/"><img src="/img/logo.png" alt="Codeforces" title="Codeforces"/></a>
    <div class="languages">
        <a href="#"><img src="/img/gb.png" alt="In English" title="In English"/></a>
        <a href="#"><img src="/img/ru.png" alt="In Russian" title="In Russian"/></a>
    </div>
    <div class="enter-or-register-box">
        <#if user??>
            <@userlink user=user nameOnly=true/>
            |
            <a href="#">Logout</a>
        <#else>
            <a href="#">Enter</a>
            |
            <a href="#">Register</a>
        </#if>
    </div>
    <nav>
        <ul>
            <#list header_links as link>
                <#if uri?starts_with(link.uri)>
                    <li><a href="${link.uri}" class="underlineHeaderLink">${link.name}</a></li>
                <#else>
                    <li><a href="${link.uri}">${link.name}</a></li>
                </#if>
            </#list>
        </ul>
    </nav>
</header>
</#macro>

<#macro sidebar>
<aside>
    <#list posts?reverse as post>
        <section>
            <div class="header">
                Post #${post.id}
            </div>
            <div class="body">
                <@postBodyChooser post, true/>
            </div>
            <div class="footer">
                <a href="/post?post_id=${post.id}">View all</a>
            </div>
        </section>
    </#list>
</aside>
</#macro>

<#macro footer>
<footer>
    <a href="#">Codeforces</a> &copy; 2010-2019 by Mike Mirzayanov
</footer>
</#macro>

<#macro page>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Codeforces</title>
    <link rel="stylesheet" type="text/css" href="/css/normalize.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/user.css">
    <link rel="stylesheet" type="text/css" href="/css/users.css">
    <link rel="stylesheet" type="text/css" href="/css/posts.css">
    <link rel="icon" href="/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
</head>
<body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
</body>
</html>
</#macro>

<#macro userlink user nameOnly>
    <#if nameOnly>
        <a class="userLink" href="/user?handle=${user.handle}">${user.name}</a>
    <#else>
        <a class="userLink coloredUserLink" href="/user?handle=${user.handle}" style="color: ${user.color.getColor()}">${user.name}</a>
    </#if>
</#macro>

<#function findAll items key id>
    <#assign postAmmount=0/>
    <#list items as item>
        <#if item[key]==id>
            <#assign postAmmount = postAmmount + 1>
        </#if>
    </#list>

    <#return postAmmount/>
</#function>

<#function findBy items key id>
    <#list items as item>
        <#if item[key]==id>
            <#return [item, items[item_index - 1]!, items[item_index + 1]!]/>
        </#if>
    </#list>
        <#return items[-1]!/>
</#function>

<#macro userArrow user arrow>
    <#if user?has_content>
        <a class="moveArrowActive" href="/user?handle=${user.handle}">${arrow}</a>
    <#else>
        <span class="moveArrowPassive">${arrow}</span>
    </#if>
</#macro>

<#macro postBody paragraph ending>
    <p>${paragraph}${ending}</p>
</#macro>

<#macro postParagraphParser post ending>
    <#list post?split("\n") as paragraph>
        <#if paragraph?has_next>
            <@postBody paragraph ""/>
        <#else>
            <@postBody paragraph ending/>
        </#if>
    </#list>
</#macro>

<#macro postBodyChooser post doCut>
    <#if doCut>
        <#if post.text?length gt 250>
            <@postParagraphParser post.text?substring(0, 250) "..."/>
        <#else>
            <@postParagraphParser post.text ""/>
        </#if>
    <#else>
        <@postParagraphParser post.text ""/>
    </#if>
</#macro>

<#macro getPostBody post doCut>
    <article>
        <div class="title">${post.title}</div>

        <div class="information">By ${findBy(users, "id", post.user_id)[0].handle}</div>
        <div class="body">
            <@postBodyChooser post doCut/>
        </div>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                2 days ago
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">68</a>
            </div>
        </div>
    </article>
</#macro>