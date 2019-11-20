<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <#if user?? && user.admin>
                    <th>Admin</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user_id"></td>
                    <td class="user_login"></td>
                    <#if user?? && user.admin>
                        <td class="user_admin">
                            <span name="isAdmin"></span>
                            <form method="post" action="">
                                <input type="submit">
                            </form>
                            <p name="processing" hidden="true">Processing...</p>
                        </td>
                    </#if>
                </tr>
            </template>
            <#if user?? && user.admin>
                <tr class="noData">
                    <td colspan="4">
                        No data
                    </td>
                </tr>
            <#else>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            </#if>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            var $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            ajax("POST",
                {
                    action: "findAll"
                },
                function (response) {
                    response["users"].forEach(function(user) {
                        $noData.hide();
                        var $tr = $($(".users template").prop("content")).clone();
                        $tr.find(".user_id").text(user["id"]);
                        $tr.find(".user_login").text(user["login"]);
                        $tr.find("tr").attr("data-userId", user["id"]);

                        var adminField = $tr.find(".user_admin");

                        if (adminField) {
                            var $spanText;
                            var $buttonText;

                            if (user["admin"]) {
                                $spanText = "true";
                                $buttonText = "Disable";
                            } else {
                                $spanText = "false";
                                $buttonText = "Enable";
                            }

                            adminField.find("span[name='isAdmin']").text($spanText);
                            adminField.find("form input").val($buttonText);
                        }

                        $(".users tbody").append($tr);
                    });
                    $(".users tr").click(function() {
                        ajax("POST",
                            {
                                action: "findUser",
                                userId: $(this).attr("data-userId")
                            },
                            function (response) {
                                notify(response["foundUser"].creationTime);
                            });
                    });

                    $(".users form").submit(function () {
                        var $span = $(this).parent().find("span[name='isAdmin']");
                        var $button = $(this).find("input");
                        var $userId = $(this).parent().parent().find(".user_id").text();
                        var $processing = $(this).parent().find("p[name='processing']");

                        $button.hide();
                        $span.hide();
                        $processing.show();

                        ajax("POST",
                            {
                                action: "changeAdmin",
                                userId: $userId
                            },
                            function (response) {
                                ajax("POST",
                                    {
                                        action: "findUser",
                                        userId: $userId
                                    },
                                    function (response) {
                                        if (response["foundUser"]["admin"]) {
                                            $span.text("true");
                                            $button.val("Disable");
                                        } else {
                                            $span.text("false");
                                            $button.val("Enable");
                                        }

                                        $processing.hide();
                                        $button.show();
                                        $span.show();
                                    });
                            });

                        return false;
                    });
                });
        })
    </script>
</@c.page>
