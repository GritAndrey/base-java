<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<br>
<h1>База данных резюме</h1>
<section>
    <table border="1" cellpadding="8" cellspacing="0" align="center">
        <thead>
        <tr>
            <th>UUID</th>
            <th>E-mail</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.resumes}" var="resume">
            <tr>
                <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume"/>
                <td>
                    <a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a>
                </td>
                <c:set var="mail" scope="session" value="${resume.getContacts(ContactType.MAIL)}"/>
                <td><a href="mailto:">${mail}</a>

                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png" alt="delete"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" alt="edit"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>