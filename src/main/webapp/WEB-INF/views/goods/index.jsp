<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>

<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="actGod" value="${ForwardConst.ACT_GOD.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>いいね 一覧</h2>
        <table id="good_list">
            <tbody>
                <tr>
                    <th class="good_name">いいねを押した人</th>
                    <th class="good_date">押した日</th>
                    <th class="good_title">押した日報のタイトル</th>
                    <th class="good_action">詳細</th>
                </tr>
                <c:forEach var="good" items="${goods}" varStatus="status">
                    <fmt:parseDate value="${good.createdAt}" pattern="yyyy-MM-dd"
                        var="goodDay" type="date" />

                    <tr class="row${status.count % 2}">
                        <td class="good_name"><c:out value="${good.employee.name}" /></td>
                        <td class="good_date"><fmt:formatDate value='${goodDay}'
                                pattern='yyyy-MM-dd' /></td>
                        <td class="good_title">${report.title}</td>
                        <td class="good_action"><a
                            href="<c:url value='?action=${actRep}&command=${commshow}$page=${report.id}' />">詳細を見る</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${goods_count} 件）<br />
            <c:forEach var="i" begin="1"
                end="${((goods_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a
                            href="<c:url value='?action=${actGod}&command=${commIdx}&page=${i}' />"><c:out
                                value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

    </c:param>
</c:import>