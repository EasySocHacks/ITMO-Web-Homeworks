<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->

<#import "commons.ftlh" as c>

<@c.page>
    <article>
        <template>
            <tr>
                <div class="title"></div>
                <div class="information"></div>
                <div class="body"></div>
                <ul class="attachment">
                    <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
                    <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
                </ul>
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
            </tr>
        </template>

        <div class="noData">
            No articles yet
        </div>
    </article>

    <script>
        $(function () {
            var $noData = $("article .noData");
            $noData.text("Loading...");

            ajax("POST",
                {
                    action: "findAllArticles"
                },
                function (response) {
                    $noData.hide();
                    response["articles"].forEach(function (article) {
                        ajax("POST",
                            {
                                action: "findUserById",
                                userId: article["userId"]
                            },
                            function (response) {
                                var $tr = $($("article template").prop("content")).clone();
                                $tr.find(".title").text(article["title"]);
                                $tr.find(".body").html(editText(article["text"]));
                                $tr.find(".information").text("Created by " + response["foundUser"].login);

                                $("article").append($tr);
                            });
                    });
                });
        });

        editText = function (text) {
            var ans = "";
            var needP = true;
            for (var i = 0; i < text.length; i++) {
                if (needP) {
                    needP = false;
                    ans += "<p>";
                }

                if (text[i] == "\n") {
                    ans += "</p>";
                    needP = true;
                } else {
                    ans = ans + text[i];
                }
            }
            ans += "</p>";

            return ans;
        }
    </script>
</@c.page>