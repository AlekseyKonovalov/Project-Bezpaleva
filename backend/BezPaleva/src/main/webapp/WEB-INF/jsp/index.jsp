<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="game">
<head>
    <title>BZP</title>
    <script src="https://code.angularjs.org/1.4.2/angular.js"></script>
    <script src="https://code.angularjs.org/1.4.2/angular-route.js"></script>
    <script src="https://code.angularjs.org/1.4.2/angular-resource.js"></script>
    <script src="js/angular.js"></script>


    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">

    <!-- Add custom CSS here -->
    <link href="css/ymapstyle.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU" type="text/javascript"></script>
    <script src="js/jscript.js" type="text/javascript"> </script>



    <script src="https://vk.com/js/api/openapi.js?136" type="text/javascript"></script>

</head>
<body class="black">



<menu></menu>

<ng-view></ng-view>

<script type="text/javascript">
    VK.init({
        apiId: 	5770707
    });
</script>
<script type="text/javascript">
    VK.Widgets.Auth('vk_auth', {});
</script>




<script>
    var str=window.location.href;
    var temp= str.indexOf("?");
    var temp2=str.indexOf("&");
    var res = str.substring(temp+5, temp2);
    if (res!="http" && res!="ht") login(res);
</script>
</body>

</html>