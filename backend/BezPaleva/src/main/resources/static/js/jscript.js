/**
 * Created by Протик on 29.11.2016.
 */
var xhr = new XMLHttpRequest();
var array;
xhr.open('GET', 'http://localhost:8080/mark?x=55&y=61&rad=10000', false);
xhr.send();
if (xhr.status != 200) {
    alert( xhr.status + ': ' + xhr.statusText ); // пример вывода: 404: Not Found
} else {
    array=JSON.parse(xhr.responseText);

    ymaps.ready(init);
}

function init () {
    myMap = new ymaps.Map("map", {
        center: [55.116272, 61.430735],
        zoom: 15
    }, {
        balloonMaxWidth: 1000,
        searchControlProvider: 'yandex#search'
    });
    for(var i=0; i<array.length;i++) {
        myPlacemark = new ymaps.Placemark([array[i].x
            , array[i].y], {

            balloonContent: array[i].description
        });
        myMap.geoObjects.add(myPlacemark);
    }
// Обработка события, возникающего при щелчке
    // левой кнопкой мыши в любой точке карты.
    // При возникновении такого события откроем балун.
    myMap.events.add('click',
        function (e) {
        if (!myMap.balloon.isOpen()) {
            var coords = e.get('coords');
            myMap.balloon.open(coords, {
                contentHeader:'Вы выбрали место',
                contentBody:'<p>Напишите описание и выберите тип метки</p>'
            });
        addMark(coords[0].toPrecision(6),coords[1].toPrecision(6));
        }
        else {
            myMap.balloon.close();
        }


    });

    function addMark (x, y)
    {


        // console.log(description);
        document.getElementById('addmarker').onclick = function()

        {
            myPlacemark = new ymaps.Placemark([x
                , y], {

                balloonContent: document.getElementById('marker_balloontext').value
            });
            myMap.geoObjects.add(myPlacemark);


            //метка на фронтенде готова

            var data = {
                x: x,
                y: y,
                type:    document.getElementById('image').value
            ,
                description: document.getElementById('marker_balloontext').value,
                photoPath: '',
                irrelevanceLevel: '',
                deathTime: 1480177654887



            };

            var boundary = String(Math.random()).slice(2);
            var boundaryMiddle = '--' + boundary + '\r\n';
            var boundaryLast = '--' + boundary + '--\r\n'

            var body = ['\r\n'];
            for (var key in data) {
                // добавление поля
                body.push('Content-Disposition: form-data; name="' + key + '"\r\n\r\n' + data[key] + '\r\n');
            }

            body = body.join(boundaryMiddle) + boundaryLast;

            // Тело запроса готово, отправляем

            var xhr = new XMLHttpRequest();
            xhr.open('POST', 'http://localhost:8080/mark?x='+x+'&y='+y+'&type='+document.getElementById('image').value+'&desc='+document.getElementById('marker_balloontext').value, true);

            xhr.setRequestHeader('Content-Type', 'multipart/form-data; boundary=' + boundary);
/*
            xhr.onreadystatechange = function() {
                if (this.readyState != 4) return;

                //alert( this.responseText );
            }
*/
            xhr.send(body);

        }

    }
}