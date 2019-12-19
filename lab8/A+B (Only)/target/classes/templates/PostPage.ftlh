<#-- @ftlvariable name="currPost" type="ru.itmo.wp.domain.Post" -->
<#-- @ftlvariable name="comments" type="ru.itmo.wp.domain.Comment[]" -->
<#-- @ftlvariable name="commentForm" type="ru.itmo.wp.domain.Comment" -->
<#import "commons.ftlh" as c>

<@c.page>
    <@c.post post=currPost/>

    <#if user??>
        <form class="post-form" method="post" action="">
            <input type="hidden" name="action" value="savePost"/>
            <div class="field">
                <div class="name">
                    <label for="text">Comment</label>
                </div>
                <div class="value">
                    <textarea id="text" name="text" style="resize: none; width: 100%; height: 10rem;">${commentForm.text!}</textarea>
                </div>
                <@c.error "commentForm.text"/>
            </div>
            <div class="button-field">
                <input type="submit" value="Save comment"/>
            </div>
        </form>
    </#if>

    <#list comments as comment>
        <@c.comment comment=comment/>
    </#list>
</@c.page>
