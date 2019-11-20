<#-- @ftlvariable name="myArticles" type="ru.itmo.wp.model.domain.Article[]" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="datatable">
        <div class="caption">My Articles</div>
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Title</th>
                    <th>Hide/Show</th>
                </tr>
                </thead>
                <tbody>
                <#if myArticles??>
                    <#list myArticles as article>
                        <tr>
                                <td name="id">${article.id}</td>
                                <td>${article.title}</td>
                                <td>

                                <div name="hiddenDiv" hidden="true">
                                    Processing...
                                </div>

                                <#if article.hidden>
                                    <form method="post" action="">
                                        <input name="changeStatus" type="submit" value="Show">
                                    </form>
                                <#else>
                                    <form method="post" action="">
                                        <input name="changeStatus" type="submit" value="Hide">
                                    </form>
                                </#if>
                                </td>
                        </tr>
                    </#list>
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
            $(".datatable form").submit(function () {
                var $id = $(this).parent().parent().find("td[name='id']").text();
                var $button = $(this).find("input[name='changeStatus']");
                var $hiddenDiv = $(this).parent().find("div[name='hiddenDiv']");

                ajax("POST",
                    {
                        action: "changeStatus",
                        id: $id
                    },
                    function (response) {
                        $button.hide();
                        $hiddenDiv.show();

                        ajax("POST",
                            {
                                action: "isHidden",
                                id: $id
                            },
                            function (response) {
                                if (response["isHidden"]) {
                                    $button.val("Show");
                                } else {
                                    $button.val("Hide");
                                }

                                $hiddenDiv.hide();
                                $button.show();
                            });
                    });
                return false;
            });
        })
    </script>
</@c.page>