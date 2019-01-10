function showHTML(message){
    var showState = 0;
    if (message == "true") {
        showState = 1;
       /* var articles = data.articles;
        console.log(articles);
        for(var i=0;i<articles.length;i++){
            var itemHtml ='<div class="stateShow" >\n' +
                '            <div class="stateShowWord">\n' +
                '                <table  border="0" cellpadding="0" cellspacing="0" class="stateTable">\n' +
                '                    <tr>\n' +
                '                        <td width="70" align="center" valign="top"><img src="images/face2.jpg" alt="你的头像" width="80" height="80" /><a>'+articles[i].nickname+'</a></td>\n' +
                '                        <td >'+articles[i].content+'</td>\n' +
                '                    </tr>\n' +
                '                </table>\n' +
                '            </div>\n'
            if(articles[i].imageURL!="empty")
                itemHtml +=
                    '            <div class="stateImgShow"><img src="'+articles[i].imageURL+'" alt="图片未找到" width = 300 align = "left"/></div>\n'
            itemHtml += '            <div class="stateShowtime"> '+articles[i].timeStamp +'</div>\n' +
                '        </div>';
            $("#contentList").append(itemHtml);
            lastID = articles[i].articleID;//数据库要改：应该返回比它小的，实际返回了比它大的
            console.log(lastID)*/
        }
    else if(message=="false"){
        showState = 2;
        /*alert("获取数据错误");*/
    }
    console.log(showState);
    return showState;
}

function showMore(length){
    var showMoreState = 0;
    if(length==0){
        showMoreState = 1;
        //alert("没有更多了~~");
    }
    else if(length>=0){
        showMoreState = 2;
        /*for (var i = 0; i < articles.length; i++) {
            var itemHtml ='<div class="stateShow" >\n' +
                '            <div class="stateShowWord">\n' +
                '                <table  border="0" cellpadding="0" cellspacing="0" class="stateTable">\n' +
                '                    <tr>\n' +
                '                        <td width="70" align="center" valign="top"><img src="images/face2.jpg" alt="你的头像" width="80" height="80" /><a>'+articles[i].nickname+'</a></td>\n' +
                '                        <td >'+articles[i].content+'</td>\n' +
                '                    </tr>\n' +
                '                </table>\n' +
                '            </div>\n'
            if(articles[i].imageURL!="empty")
                itemHtml +=
                    '            <div class="stateImgShow"><img src="'+articles[i].imageURL+'" alt="图片未找到" width = 300 align = "left"/></div>\n'
            itemHtml += '            <div class="stateShowtime"> '+articles[i].timeStamp +'</div>\n' +
                '        </div>';
            lastID = articles[i].articleID;
            $(warrper).append(itemHtml);
            $(".detail").animate({opacity:1},1000);
        }*/
    }
    console.log(showMoreState);
    return showMoreState;
}
var lastID =0;
Search = function(){
    alert("Hello!Search()!");
    $.ajax({
        type : "POST",
        data : JSON.stringify({
            show:parseInt("5"),
            articleID:parseInt("0")}),
        datatype : "json",
        contentType: 'application/json;charset=utf-8',
        url : "http://localhost:8080/timeline/article/refresh",
        success: function(data){
            showHTML(data.message)
        },
        error: function(xhr,status,err){
            alert(xhr.status);
            alert(xhr.readyState);
            alert("连接失败！ " + status + ": " + err.toString());
        }
    })
}

loadMore = function(){
    alert("hello loadMore");
    var warrper = "#contentList";   //定义加载内容的容器
    var clickBtn = "#addmore_btn"     //绑定点击按钮

    $(document).ready(function() {
        getData();
        $(clickBtn).click(function(){
            getData();
            //加载菊花
            $(document).ajaxStart(function () {
                $("#loading").show();
            });
            $(document).ajaxComplete(function () {
                setTimeout(1000,$("#loading").hide());
            });
        })
    })
    var getData = function(){
        $.ajax({
            type : "POST",
            data : JSON.stringify({
                show:parseInt("6"),
                articleID:lastID}),
            datatype : "json",
            contentType: 'application/json;charset=utf-8',
            url : "http://localhost:8080/timeline/article/more",
            success: function(data){
                console.log("请求成功");
                if (data.message == "true") {
                    appendHtml(data.articles);
                    /* for(var i=0;i<data.num;i++){
                         document.getElementById("nickname"+i).innerHTML= articles[i].nickname;
                         document.getElementById("content"+i).innerHTML= articles[i].content;
                         document.getElementById("timestamp"+i).innerHTML= articles[i].timestamp;
                         $("#image"+i).attr("src",articles[i].imageURL);
                     }*/
                } else {
                    alert("未发表成功，请重试");
                }
            },
            error: function(xhr,status,err){
                alert(xhr.status);
                alert(xhr.readyState);
                alert("连接失败！ " + status + ": " + err.toString());
            }
        })
    }
    var appendHtml = function(articles){
        showMore(articles.length);
    }


}