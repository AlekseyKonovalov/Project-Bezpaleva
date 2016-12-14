/**
 * Created by Протик on 29.11.2016.
 */
var Coords;
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
} //функция определения цвета вследствие типа метки


//1.Получаем все метки с сервера/////////////////////////////
var xhr = new XMLHttpRequest();
var array;
xhr.open('GET', 'mark?x=55&y=61&rad=10000', false);
xhr.send();
if (xhr.status != 200) {
    alert( xhr.status + ': ' + xhr.statusText ); // пример вывода: 404: Not Found
} else {
    array=JSON.parse(xhr.responseText);

    ymaps.ready(init);
}
////////////////////////////////////////////////////////////


//2.Инициализация карты- выводим все метки на карту/////////
function init () {
    myMap = new ymaps.Map("map", {
        center: [55.160026, 61.419069],
        zoom: 10
    }, {
       // balloonWidth: 1000,
        searchControlProvider: 'yandex#search'
    });
    for(var i=0; i<array.length;i++) {
        var color=getColor(array[i].type);
        myPlacemark = new ymaps.Placemark([array[i].x
            , array[i].y], {

            balloonContent:
            'x:'+
            array[i].x+'  y:'+
                array[i].y+
            '<h3> Описание: </h3>'+
            '<h4>'+
            array[i].description+
            '</h4>'+
            '<h3>Редактирование</h3>'+
            ['<input class="form-control" name="balloon_text" id="marker_balloontext" rows="5" cols="25"></input>',
            '<select name="image" id="image" class="form-control">',
            '<option value="dps">ДПС</option>',
            '<option value="camera">Камера</option>',
            '<option value="help">Помощь</option>',
            '<option value="other">Другое</option>',

            '</select>',

            '<label for="submit">&nbsp;</label><input class="btn btn-lg btn-info" type="submit" name="submit" id="addmarker" value="Редактировать" onclick=changeMark(Coords[0],Coords[1],document.getElementById("marker_balloontext").value,document.getElementById("image").value)>'
    ]


        },
            {
                iconColor: color

            }
        );
        myMap.geoObjects.add(myPlacemark);
    }
////////////////////////////////////////////////////////////
//3. Делаем карту кликабельной- балун представляется собой форму для ввода с кнопкой для вызова ф-ии добавления метки
    myMap.events.add('click',
        function (e) {
        if (!myMap.balloon.isOpen()) {
            Coords = e.get('coords');
            myMap.balloon.open(Coords, {

                contentHeader:'Описание',
                contentBody: ['<input class="form-control" name="balloon_text" id="marker_balloontext" rows="5" cols="25"></input>',
                '<select name="image" id="image" class="form-control">',
                '<option value="dps">ДПС</option>',
                '<option value="camera">Камера</option>',
                '<option value="help">Помощь</option>',
                '<option value="other">Другое</option>',

                '</select>',

                '<label for="submit">&nbsp;</label><input class="btn btn-lg btn-info" type="submit" name="submit" id="addmarker" value="Добавить" onclick=addMark(Coords[0],Coords[1],document.getElementById("marker_balloontext").value,document.getElementById("image").value)>'
                ]
            });
        }
        });
}
//сама функция
function addMark (x, y,desc,type)
{
    console.log("Сработал эддмарк");
    //console.log(x,y);

    var color=
        getColor (type);

    var myPlacemark = new ymaps.Placemark([x
        , y]
    ,{iconColor:color});
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
        deathTime: 1480177654887,
        user: 3
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
    xhr.open('POST', 'mark?x='+x+'&y='+y+'&type='+type+'&desc='+desc+'&userId=3', true);
    xhr.setRequestHeader('Content-Type', 'multipart/form-data; boundary=' + boundary);
    /*
     xhr.onreadystatechange = function() {
     if (this.readyState != 4) return;

     //alert( this.responseText );
     }
     */
    xhr.send(body);
    myMap.balloon.close();
}

function changeMark (x, y,desc,type)
{
    console.log("Сработал ченджмарк");
    //console.log(x,y);
/*
    var color=
        getColor (type);
*/
    var myPlacemark = new ymaps.Placemark([x
            , y]
        ,{iconColor:color});
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
        deathTime: 1480177654887,
        user: 3
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
    xhr.open('POST', 'changeMark?id=3&desc='+desc+'&irrel=100000000', true);
    xhr.setRequestHeader('Content-Type', 'multipart/form-data; boundary=' + boundary);
    /*
     xhr.onreadystatechange = function() {
     if (this.readyState != 4) return;

     //alert( this.responseText );
     }
     */
    xhr.send(body);
    myMap.balloon.close();
}
