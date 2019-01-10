var showID = 0;
var userArray = new Array();
var articleArray = new Array();
var lastID =0;
Search = function(){
    $.ajax({
        type : "POST",
        data : JSON.stringify({
            show:parseInt("5"),
            articleID:parseInt("0")}),
        datatype : "json",
        contentType: 'application/json;charset=utf-8',
        url : "http://129.204.74.128:8080/timeline/article/refresh",
        success: function(data){
            if (data.message == "true") {
                var articles = data.articles;
                console.log(articles);
                for(var i=0;i<articles.length;i++){
                    var itemHtml ='<div class="stateShow">\n' +
                        '            <div class="stateShowWord">\n' +
                        '                <table  border="0" cellpadding="0" cellspacing="0" class="stateTable">\n' +
                        '                    <tr>\n' +
                        '                        <td width="70" align="center" valign="top"><img src="images/face.png" alt="你的头像" width="80" height="80" /><a>'+articles[i].nickname+'</a></td>\n' +
                        '                        <td >'+articles[i].content+'</td>\n' +
                        '                    </tr>\n' +
                        '                </table>\n' +
                        '            </div>\n'
                    if(articles[i].imageURL!="empty")
                        itemHtml +=
                        '            <div class="stateImgShow"><img src="'+articles[i].imageURL+'" alt="图片未找到" width = 300 align = "left"/></div>\n'
                        itemHtml += '            <div class="stateShowtime"> '+articles[i].timeStamp +'</div>\n' +
                            '<a myAttr='+showID+' href="javascript:void(0);" id="delete-btn" onclick="deleteOne()">删除</a>'+
                        '        </div>';
                    $("#contentList").append(itemHtml);
                    userArray[showID] = articles[i].userID;
                    articleArray[showID] = articles[i].articleID;
                    showID++;
                    lastID = articles[i].articleID;
                    console.log(lastID)
                }

            } else {
                alert("获取数据错误");
            }
        },
        error: function(xhr,status,err){
            alert(xhr.status);
            alert(xhr.readyState);
            alert("连接失败！ " + status + ": " + err.toString());
        }
    })
}

loadMore = function() {
    $.ajax({
        type: "POST",
        data: JSON.stringify({
            show: parseInt("4"),
            articleID: lastID
        }),
        datatype: "json",
        contentType: 'application/json;charset=utf-8',
        url: "http://129.204.74.128:8080/timeline/article/more",
        success: function (data) {
            console.log("请求成功");
            if (data.message == "true") {
                appendHtml(data.articles);
            } else {
                alert("未发表成功，请重试");
            }
        },
        error: function (xhr, status, err) {
            alert(xhr.status);
            alert(xhr.readyState);
            alert("连接失败！ " + status + ": " + err.toString());
        }
    })

    var appendHtml = function (articles) {
        if (articles.length == 0) {
            alert("没有更多了~~");
            return false;
        }
        for (var i = 0; i < articles.length; i++) {
            var itemHtml = '<div class="stateShow" >\n' +
                '            <div class="stateShowWord">\n' +
                '                <table  border="0" cellpadding="0" cellspacing="0" class="stateTable">\n' +
                '                    <tr>\n' +
                '                        <td width="70" align="center" valign="top"><img src="images/face.png" alt="你的头像" width="80" height="80" /><a>' + articles[i].nickname + '</a></td>\n' +
                '                        <td >' + articles[i].content + '</td>\n' +
                '                    </tr>\n' +
                '                </table>\n' +
                '            </div>\n'
            if (articles[i].imageURL != "empty")
                itemHtml +=
                    '            <div class="stateImgShow"><img src="' + articles[i].imageURL + '" alt="图片未找到" width = 300 align = "left"/></div>\n'
            itemHtml += '            <div class="stateShowtime"> ' + articles[i].timeStamp + '</div>\n' +
                '<a myAttr=' + showID + ' href="javascript:void(0);" id="delete-btn" onclick="deleteOne()">删除</a>' +
                '        </div>';
            lastID = articles[i].articleID;
            userArray[showID] = articles[i].userID;
            articleArray[showID] = articles[i].articleID;
            showID++;
            console.log(lastID);
            $("#contentList").append(itemHtml);
            $(".detail").animate({opacity: 1}, 1000);
        }
    }

}


deleteOne = function(){
    var targ
    if (!e) var e = window.event
    if (e.target) targ = e.target
    else if (e.srcElement) targ = e.srcElement
    if (targ.nodeType == 3) // defeat Safari bug
        targ = targ.parentNode
    var tname
    tname=targ.tagName
    if(tname != "A")
        return false;
    var theID = targ.getAttribute("myAttr")
    console.log(theID);
    if(userArray[theID] =="")
        return false;
    if(userArray[theID]!=sessionStorage.userID){
        alert("你不是该用户！不能执行删除操作！");
        return false;
    }
    $.ajax({
        type : "POST",
        data : JSON.stringify({
            articleID: articleArray[theID]}),
        datatype : "json",
        contentType: 'application/json;charset=utf-8',
        url : "http://129.204.74.128:8080/timeline/article/delete",
        success: function(data){
            console.log(data)
            if (data.message == "true") {
                alert("删除成功！");
                window.location.href = "http://129.204.74.128:8080/timeline/views/index.html";
            } else {
                alert("删除失败，请重新操作");
            }
        },
        error: function(xhr,status,err){
            alert(xhr.status);
            alert(xhr.readyState);
            alert("连接失败！ " + status + ": " + err.toString());
        }
    })

}