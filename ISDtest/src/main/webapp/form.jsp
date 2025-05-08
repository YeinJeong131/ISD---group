<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${device == null ? 'Add New Device' : 'Edit Device'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container { margin-top: 30px; }
        .form-group { margin-bottom: 20px; }
    </style>
</head>
<body>
<div class="container">
    <h2>${device == null ? 'Add New Device' : 'Edit Device'}</h2>

    <form action="${pageContext.request.contextPath}/device/${device == null ? '' : 'update'}"
          method="post" class="needs-validation" novalidate>

        <c:if test="${device != null}">
            <input type="hidden" name="id" value="${device.id}">
        </c:if>

        <div class="form-group">
            <label for="name">Device Name</label>
            <input type="text" class="form-control" id="name" name="name"
                   value="${device.name}" required>
            <div class="invalid-feedback">Please enter the device name</div>
        </div>

        <div class="form-group">
            <label for="type">Device Type</label>
            <input type="text" class="form-control" id="type" name="type"
                   value="${device.type}" required>
            <div class="invalid-feedback">Please enter the device type</div>
        </div>

        <div class="form-group">
            <label for="price">Price</label>
            <div class="input-group">
                <span class="input-group-text">￥</span>
                <input type="number" class="form-control" id="price" name="price"
                       value="${device.price}" step="0.01" min="0" required>
                <div class="invalid-feedback">Please enter a valid price</div>
            </div>
        </div>

        <div class="form-group">
            <label for="quantity">Stock Quantity</label>
            <input type="number" class="form-control" id="quantity" name="quantity"
                   value="${device.quantity}" min="0" required>
            <div class="invalid-feedback">Please enter a valid stock quantity</div>
        </div>

        <button type="submit" class="btn btn-primary">Save</button>
        <a href="${pageContext.request.contextPath}/device" class="btn btn-secondary">Back</a>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    (function () {
        'use strict'
        var forms = document.querySelectorAll('.needs-validation')
        Array.prototype.slice.call(forms).forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                form.classList.add('was-validated')
            }, false)
        })
    })()
</script>
</body>
</html>