<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="game">
<head>
    <title>BZP</title>
    <script src="https://code.angularjs.org/1.4.2/angular.js"></script>
    <script src="https://code.angularjs.org/1.4.2/angular-route.js"></script>
    <script src="https://code.angularjs.org/1.4.2/angular-resource.js"></script>
    <script src="js/angular.js"></script>

    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU" type="text/javascript"></script>
    <script type="text/javascript">
        ymaps.ready(init);
        var myMap,
                myPlacemark;

        function init(){
            myMap = new ymaps.Map("map", {
                center: [55.116272, 61.430735],
                zoom: 15
            });

            myPlacemark = new ymaps.Placemark([55.116272, 61.430735], {

                balloonContent: 'Активные'
            });

            myMap.geoObjects.add(myPlacemark);
        }
    </script>
</head>
<body>
{{2+2}}
<menu></menu>

<ng-view></ng-view>

</body>

</html>