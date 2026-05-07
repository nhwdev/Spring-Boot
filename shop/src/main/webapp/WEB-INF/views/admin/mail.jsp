<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>메일 보내기</title>
</head>
<body>
<h2>메일 보내기</h2>
<%-- action="mail"은 컨트롤러의 @PostMapping("mail")과 연결됩니다 --%>
<form:form modelAttribute="mail" name="mailform" action="mail" enctype="multipart/form-data">
    <table class="table">
        <tr>
            <td>발신 계정</td>
                <%-- 서버 설정값인 senderEmail을 보여주거나, 단순히 안내 문구로 대체 --%>
            <td><strong>${mail.googleid}</strong> (시스템 설정 계정)</td>
        </tr>
        <tr>
            <td>받는사람</td>
            <td>
                <form:input path="recipient" class="form-control" readonly="true"/>
                <small class="text-muted">목록에서 선택된 수신자입니다.</small>
            </td>
        </tr>
        <tr>
            <td>제목</td>
            <td>
                <form:input path="title" class="form-control"/>
                <form:errors path="title" class="text-danger"/>
            </td>
        </tr>
        <tr>
            <td>메시지 형식</td>
            <td>
                    <%-- DTO의 mtype 필드와 연결되도록 path 사용 권장 --%>
                <select name="mtype" class="form-control">
                    <option value="text/html; charset=utf-8">HTML</option>
                    <option value="text/plain; charset=utf-8">TEXT</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>첨부파일</td>
            <td>
                <input type="file" name="file1" class="form-control" multiple>
                <small class="text-muted">여러 파일을 선택할 수 있습니다.</small>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <form:textarea path="contents" class="form-control" id="summernote"/>
                <form:errors path="contents" class="text-danger"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" class="text-center">
                <button type="submit" class="btn btn-primary">메일 보내기</button>
                <button type="button" class="btn btn-secondary" onclick="history.back()">취소</button>
            </td>
        </tr>
    </table>
</form:form>

<script type="text/javascript">
    $(document).ready(function () {
        $('#summernote').summernote({
            height: 300,
            callbacks: {
                onImageUpload: function (files) {
                    for (let i = 0; i < files.length; i++) {
                        sendFile(files[i]);
                    }
                }
            }
        });
    });

    function sendFile(file) {
        let data = new FormData();
        data.append("image", file);
        $.ajax({
            url: "${path}/ajax/uploadImage", // 경로 확인 필요
            type: "post",
            data: data,
            processData: false,
            contentType: false,
            success: function (url) {
                // 서버에서 리턴해준 이미지 경로(url)를 에디터에 삽입
                $("#summernote").summernote("insertImage", url);
            },
            error: function (e) {
                alert("이미지 업로드 실패: " + e.status);
            }
        });
    }
</script>
</body>
</html>