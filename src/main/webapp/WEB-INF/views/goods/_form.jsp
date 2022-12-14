<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<label>氏名</label><br />
<c:out value="${sessionScope.login_employee.name}" />
<br /><br />

<p>いいねする日報:${good.report.title}</p><br />
<br /><br />

<label for="${AttributeConst.GOD_CONTENT.getValue()}">内容</label><br />
<textarea  name="${AttributeConst.GOD_CONTENT.getValue()}" id="${AttributeConst.GOD_CONTENT.getValue()}" rows="10" cols="50">${good.content}</textarea>
<br /><br />

<input type="hidden" name="${AttributeConst.GOD_ID.getValue()}" value="${good.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">いいね</button>