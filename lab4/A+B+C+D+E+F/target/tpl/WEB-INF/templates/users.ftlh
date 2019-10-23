<#import "common.ftl" as c/>

<@c.page>
    <div class="datatable">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Handle</th>
                <th>User</th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
                <tr>
                    <td>${user.id}</td>
                    <td>${user.handle}</td>
                    <td><@c.userlink user false/></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
