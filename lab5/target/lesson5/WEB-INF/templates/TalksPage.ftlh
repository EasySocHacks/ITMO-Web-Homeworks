<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="userSelection" type="java.lang.String" -->
<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box message-box">
        <div class="header">Send Message</div>
        <form method="post" action="">
            <input type="hidden" name="action" value="sendMessage"/>
            <div class="field">
                <div class="name">
                    <label for="userSelection">Select a user</label>
                </div>
                <div class="value">
                    <select id="userSelection" name="userSelection">
                        <#list users as currentUser>
                            <#if currentUser.login != user.login>
                                <#if userSelection?? && userSelection == currentUser.login>
                                    <option selected="selected">${currentUser.login}</option>
                                <#else>
                                    <option>${currentUser.login}</option>
                                </#if>
                            </#if>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="field">
                <div class="name">
                    <label for="text">Message</label>
                </div>
                <div class="value">
                    <textarea id="text" name="text" type="text"><#if text??>${text}</#if></textarea>
                </div>
            </div>
            <#if error??>
                <div class="error">
                    ${error!}
                </div>
            </#if>
            <div class="button-field">
                <input type="submit" value="Send message">
            </div>
        </form>
    </div>

    <div class="datatable">
        <div class="caption">Messages</div>
        <table>
            <thead>
            <tr>
                <th>From -> To</th>
                <th class="messageColumn">Message</th>
            </tr>
            </thead>
            <tbody>
            <#if talks?size == 0>
                <tr>
                    <td colspan="2">
                        No messages yet...
                    </td>
                </tr>
            </#if>
            <#list talks as talk>
                <tr>
                    <td>${findUserLoginById(talk.sourceUserId)} -> ${findUserLoginById(talk.targetUserId)}</td>
                    <td class="messageColumn">
                        <#list talk.text?split("\r\n") as line>
                            ${line}<br>
                        </#list>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>

<#function findUserLoginById id>
    <#list users as user>
        <#if user.id == id>
            <#return user.login/>
        </#if>
    </#list>

    <#return ""/>
</#function>