$(function () {
    $('.share').click(function () {
        var json = {
            action: "share",
            params: {
                imageUrl:"http://i9.qhimg.com/t017d891ca365ef60b5.jpg",
                url:"https://www.baidu.com",
                title:"标题",
                text:"描述"
            }
        };

        creamsoda.event(JSON.stringify(json));

    });

    $('.comment').click(function () {

            var json = {
                action: "comment"
            };

            creamsoda.event(JSON.stringify(json));

        });
    $('.zan').click(function () {

                var json = {
                    action: "comment"
                };

                creamsoda.event(JSON.stringify(json));

            });
});