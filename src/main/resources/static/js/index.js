$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");

	// 获取标题 和 内容
	var title = $("#recipient-name").val();
	var content = $("#message-text").val();

	// 发送异步请求
	$.post(
		// 路径
		CONTEXT_PATH + "/discuss/add",
		// 内容
		{"title":title,"content":content},
		// 回调函数，处理返回的数据
		function (data){
			data = $.parseJSON(data);
			// 在提示框中显示提示信息
			$("#hintBody").text(data.msg);
			// 显示提示框
			$("#hintModal").modal("show");
			// 2秒后，自动隐藏提示框
			setTimeout(function(){
				$("#hintModal").modal("hide");
				// 刷新页面
				if(data.code == 0){
					window.location.reload();
				}
			}, 2000);
		}
	)



}