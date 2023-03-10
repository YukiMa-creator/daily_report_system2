<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>

<c:set var="actGod" value="${ForwardConst.ACT_GOD.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commDesty" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>いいね 編集ページ</h2>
        <form method="POST" action="<c:url value='?action=${actGod}&command=${commUpd}' />"><c:import url="_form.jsp" />
        </form>

        <p><a href="#" onclick="confirmDestroy();">このいいねを削除する</a></p>
        <form method="POST" action="<c:url value='?action=${actGod}&command=${commDesty}' />">
            <input type="hidden" name="_token" value="${_token}" />
        </form>
        <script>
        function confirmDestroy() {
            if(confirm("本当に削除してよろしいですか？")) {
                document.forms[1].submit();
            }
        }
        </script>
        <p><c:out value="${sessionScope.report.title}"/></p>

        <p>
            <a href="<c:url value='?action=Good&command=index' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>