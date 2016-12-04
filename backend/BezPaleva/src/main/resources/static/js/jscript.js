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

function getColor(type) {
    var color;
    switch (type)
    {
        case 'dps': color='#0411c1'
            break;
        case 'help' : color='#fb4e14'
            break;
        case 'other' :color='#3caa3c'
            break;
        case 'camera' : color='#e2f005'
            break;
        default: color='#3caa3c'
    }
return color;
}


function init () {
    myMap = new ymaps.Map("map", {
        center: [55.160026, 61.419069],
        zoom: 10
    }, {
        balloonMaxWidth: 1000,
        searchControlProvider: 'yandex#search'
    });
    for(var i=0; i<array.length;i++) {
        var color=getColor(array[i].type);
        myPlacemark = new ymaps.Placemark([array[i].x
            , array[i].y], {

            balloonContent: array[i].description},
            {
                iconColor: color
            }
            // #fb4e14 красный
            // #0411c1 синий
            // #3caa3c зеленый
            // #e2f005 желтый

        );
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
        var desc=document.getElementById('marker_balloontext').value;
        var type=document.getElementById('image').value;
        var color=
        getColor (type);
        document.getElementById('addmarker').onclick = function()

        {
            var myPlacemark = new ymaps.Placemark([x
                , y], {

                balloonContent: desc
            },{iconColor:color});
            myMap.geoObjects.add(myPlacemark);


            //метка на фронтенде готова

            var data = {
                x: x,
                y: y,
                type: type
            ,
                description: desc,
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