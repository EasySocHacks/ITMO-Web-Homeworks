<#-- @ftlvariable name="createNoticeForm" type="ru.itmo.wp.form.NoticeCredentials" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="createNotice form-box">
        <div class="header">Create Notice</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="createNotice"/>
                <div class="field">
                    <div class="name">
                        <label for="content">Notice content</label>
                    </div>
                    <div class="value">
                        <textarea id="content" name="content">${createNoticeForm.content!}</textarea>
                    </div>
                    <@c.error "createNoticeForm.content"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Add">
                </div>
            </form>
        </div>
    </div>
</@c.page>