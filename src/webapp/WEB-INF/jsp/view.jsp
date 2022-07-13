<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<br>
<jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
<h1>Резюме ${resume.fullName}</h1>
<a href="?uuid=${resume.uuid}&action=edit"></a>
<section>
    <h2>${resume.fullName}&nbsp;<a href="?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" alt="edit"></a>
    </h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>