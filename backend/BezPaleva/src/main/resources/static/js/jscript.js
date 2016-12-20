/**
 * Created by Протик on 29.11.2016.
 */
var array=[];
var Coords=[];
var CoordsUpdate=[];
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
//var array;
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

        CoordsUpdate[0]=array[i].x;
       CoordsUpdate[1]=array[i].y;


        myPlacemark = new ymaps.Placemark([CoordsUpdate[0]
            , CoordsUpdate[1]], {

            balloonContent:
            '<p id="x">'+CoordsUpdate[0]+
                '</p>'+
                    '<p id="y">'+
                CoordsUpdate[1]+
                '</p>'+
            '<h3> Описание: </h3>'+
            '<h4>'+
            array[i].description+
            '</h4>'+
            '<label for="submit">&nbsp;</label><input class="btn btn-lg btn-info" type="submit" name="submit" id="priorityUpdater" value="Не актуально" onclick=changePriority(document.getElementById("x").innerHTML,document.getElementById("y").innerHTML)>'
            +
            '<h3>Редактирование</h3>'+
            ['<input class="form-control" name="balloon_text" id="marker_balloontext2" rows="5" cols="25"></input>'+
            '<select name="image" id="image2" class="form-control">'+
            '<option value="dps">ДПС</option>',
            '<option value="camera">Камера</option>',
            '<option value="help">Помощь</option>',
            '<option value="other">Другое</option>',

            '</select>'+

                '<label for="submit">&nbsp;</label><input class="btn btn-lg btn-info" type="submit" name="submit" id="addmarker" value="Редактировать" onclick=changeMark(document.getElementById("x").innerHTML,document.getElementById("y").innerHTML,document.getElementById("marker_balloontext2").value,document.getElementById("image2").value)>'
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
        , y],{ balloonContent:  'x:'+
        x+'  y:'+
        y+
        '<h3> Описание: </h3>'+
        '<h4>'+
        desc+
        '</h4>'}
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
        irrelevanceLevel: 999999999999,
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

     xhr.onreadystatechange = function() {
     if (this.readyState != 4) return;

        array.push(JSON.parse(xhr.responseText));
/*
        for(var i=0; i<array.length;i++)
        console.log(array[i].id);
*/
     }

    xhr.send(body);
    myMap.balloon.close();
}



////////////////////////////////////////////////////

function changeMark1(x,y,desc,type) {
    console.log(x);
    console.log(y);
    console.log(desc);
    console.log(type);



}

function changeMark (x, y,desc,type)
{
    console.log("Сработал ченджмарк");
    //console.log(x,y);
/*
    var color=
        getColor (type);
*/
    var id=getIDfromCoords(x,y);
    var color=getColor(getTypefromCoords());
var myPlacemark = new ymaps.Placemark([x
            , y],{ balloonContent:  'x:'+
    x+'  y:'+
    y+
    '<h3> Описание: </h3>'+
    '<h4>'+
    desc+
    '</h4>'}
        ,{iconColor:color});
    myMap.geoObjects.add(myPlacemark);
    //метка на фронтенде готова
    var data = {
        id: id,
        type: type,
        description: desc,
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
    xhr.open('POST', 'changeMark?'+'id='+id+'&desc='+desc+'&type='+type, true);
    xhr.setRequestHeader('Content-Type', 'multipart/form-data; boundary=' + boundary);

     xhr.onreadystatechange = function() {
     if (this.readyState != 4) return;

         array.push(JSON.parse(xhr.responseText));
     }

    xhr.send(body);

    myMap.balloon.close();
}
////////////////////////////////////////////////////////////
function getIDfromCoords(x,y)
{
   var id;
    for(var i=0; i<array.length;i++)
    {
        if ((x==array[i].x)&&(y==array[i].y))
        {
          id=array[i].id;
        }
    }
return id;
}
function getTypefromCoords(x,y)
{
    var type;
    for(var i=0; i<array.length;i++)
    {
        if ((x==array[i].x)&&(y==array[i].y))
        {
            id=array[i].type;
        }
    }
    return type;
}

function changePriority(x,y)
{
    console.log("Сработал ченджриорити");

    var id=getIDfromCoords(x,y);

    var data = {
        id: id,
        irrelevanceLevel: 1

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
    xhr.open('POST', 'changeMark?'+'id='+id+'&irrel=1', true);
    xhr.setRequestHeader('Content-Type', 'multipart/form-data; boundary=' + boundary);
/*
    xhr.onreadystatechange = function() {
        if (this.readyState != 4) return;

        console.log(JSON.parse(xhr.responseText));
    }
*/
    xhr.send(body);

    myMap.balloon.close();
}


function test(x) {
    VK.Auth.login();
}

function kek(str)
{
    var temp= str.indexOf("?");
    var temp2=str.indexOf("&");
    var res = str.substring(temp+5, temp2);
    alert(res);


}