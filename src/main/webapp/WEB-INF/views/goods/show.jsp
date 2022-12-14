<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>

<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="actGod" value="${ForwardConst.ACT_GOD.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
<c:param name="content">
 <h3>いいね 詳細ページ</h3>

        <table>
            <tbody>
                <tr>
                    <th>いいねした人</th>
                    <td><c:out value="${good.employee.name}" /></td>
                </tr>
                <tr>
                    <th>日報のタイトル</th>
                    <td><c:out value="${good.report.title}" /></td>
                </tr>
                <tr>
                    <th>内容</th>
                    <td><pre><c:out value="${good.content}" /></pre></td>
                </tr>
                <tr>
                    <th>登録日時</th>
                    <fmt:parseDate value="${good.createdAt}" pattern="yyyy-MM-dd" var="createDay" type="date" />
                    <td><fmt:formatDate value="${createDay}" pattern="yyyy-MM-dd" /></td>
                </tr>
            </tbody>
        </table>

        <c:if test="${sessionScope.login_employee.id == good.employee.id}">
            <p>
                <a href="<c:url value='?action=${actGod}&command=${commEdt}&id=${good.id}' />">いいねの内容を編集する</a>
            </p>
        </c:if>
        <p>
                <a href="<c:url value='?action=${actGod}&command=${commIdx}' />">いいねの一覧に戻る</a>
            </p>

</c:param>
</c:import>
