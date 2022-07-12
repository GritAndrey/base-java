<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <th>Full name</th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.resumes}" var="resume">
            <tr>
                <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume"/>
                <td>"${resume.uuid}"</td>
                <td>"${resume.fullName}"</td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>