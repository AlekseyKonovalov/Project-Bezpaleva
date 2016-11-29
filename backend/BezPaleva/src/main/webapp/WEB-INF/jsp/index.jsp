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
        // 1. Создаём новый объект XMLHttpRequest
        var xhr = new XMLHttpRequest();
        var array;
        // 2. Конфигурируем его: GET-запрос на URL 'phones.json'
        xhr.open('GET', 'http://localhost:8080/mark?x=55&y=61&rad=10000', false);

        // 3. Отсылаем запрос
        xhr.send();

        // 4. Если код ответа сервера не 200, то это ошибка
        if (xhr.status != 200) {
// обработать ошибку
            alert( xhr.status + ': ' + xhr.statusText ); // пример вывода: 404: Not Found
        } else {
// вывести результат
            //alert( xhr.responseText ); // responseText -- текст ответа.
       array=JSON.parse(xhr.responseText);

       ymaps.ready(init);
            function init(){
                myMap = new ymaps.Map("map", {
                    center: [55.116272, 61.430735],
                    zoom: 15
                });
                //alert(array.length);
                for(var i=0; i<array.length;i++) {
                    myPlacemark = new ymaps.Placemark([array[i].x
                        , array[i].y], {

                        balloonContent: array[i].description
                    });

                    myMap.geoObjects.add(myPlacemark);
                }
                }
        }




    </script>
</head>
<body>
{{2+2}}
<menu></menu>

<ng-view></ng-view>

</body>

</html>