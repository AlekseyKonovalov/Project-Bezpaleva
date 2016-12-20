/**
 * Created by Протик on 29.11.2016.
 */
var array=[];
var Coords=[];
var CoordsUpdate=[];
var UserID="annon";
var Time=new Date();
Time=Time.getTime()-300000;

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
       // if(UserID!=array[i].user) document.getElementById('addmarker').disabled = true;
        var curMarkID=array[i].user;
        var changePriorityFlag=0;
        myPlacemark = new ymaps.Placemark([CoordsUpdate[0]
            , CoordsUpdate[1]],  {

            //БАЛУН!!!!!!!!!!!!!!!!!!!!!!!!!!!
            balloonContent:

            '<p id="x" style="display: none">'+CoordsUpdate[0]+
                '</p>'+
                    '<p id="y" style="display: none">'+
                CoordsUpdate[1]+
                '</p>'+
            '<p id="user" style="display: none">'+ curMarkID+'</p>'+
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

                '<label for="submit">&nbsp;</label><input class="btn btn-lg btn-info" type="submit" name="submit" id="addmarker" value="Редактировать" onclick=changeMark(document.getElementById("x").innerHTML,document.getElementById("y").innerHTML,document.getElementById("marker_balloontext2").value,document.getElementById("image2").value,document.getElementById("user").innerHTML)>'
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
        if (!myMap.balloon.isOpen()&&(UserID!="annon")) {
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
        , y],{ balloonContent:
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
        user: UserID
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
    xhr.open('POST', 'mark?x='+x+'&y='+y+'&type='+type+'&desc='+desc+'&userId='+UserID, true);
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
     //alert(xhr.responseText);
    myMap.balloon.close();
}



////////////////////////////////////////////////////

function changeMark (x, y,desc,type,user)
{
    //alert(user);
    if(UserID==user) {
        console.log("Сработал ченджмарк");
        //console.log(x,y);
        /*

         var color=
         getColor (type);
         */
        var id = getIDfromCoords(x, y);
        var color = getColor(getTypefromCoords());
        var myPlacemark = new ymaps.Placemark([x
                , y], {
                balloonContent: 'x:' +
                x + '  y:' +
                y +
                '<h3> Описание: </h3>' +
                '<h4>' +
                desc +
                '</h4>'
            }
            , {iconColor: color});

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
        xhr.open('POST', 'changeMark?' + 'id=' + id + '&desc=' + desc + '&type=' + type, true);
        xhr.setRequestHeader('Content-Type', 'multipart/form-data; boundary=' + boundary);

        xhr.onreadystatechange = function () {
            if (this.readyState != 4) return;

            array.push(JSON.parse(xhr.responseText));
        }

        xhr.send(body);
        myMap.geoObjects.add(myPlacemark);
        myMap.balloon.close();
        alert("Метка успешно отредактирована");
    }
else alert("У вас нет права редактировать чужую метку");
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
    var curTime=new Date();
    curTime=curTime.getTime();
    if(UserID!="annon") {
        if (curTime - Time > 300000) {


            console.log("Сработал ченджриорити");

            var id = getIDfromCoords(x, y);

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
            xhr.open('POST', 'changeMark?' + 'id=' + id + '&irrel=1', true);
            xhr.setRequestHeader('Content-Type', 'multipart/form-data; boundary=' + boundary);
            /*
             xhr.onreadystatechange = function() {
             if (this.readyState != 4) return;

             console.log(JSON.parse(xhr.responseText));
             }
             */
            xhr.send(body);

            myMap.balloon.close();
            Time=curTime;
            alert("Вы понизили актуальность метки");
        }
        else alert("Вы уже понижали актуальность в течение 5 минут");
    }
    else alert("Войдите, чтобы понижать актуальность меток");

}


function login(idVK)
{
    var x = new XMLHttpRequest();

    x.open("GET", 'user?vkId='+idVK, false);
    x.send();


    //alert(x.status);

    if (x.responseText == "") {
        //запись в нашу базу

        //alert("нет");
        var xhr2 = new XMLHttpRequest();

        var body = 'fName=' + encodeURIComponent("Чувак") +
            'lName=' + encodeURIComponent("Чуваков")+
            'vkId=' + encodeURIComponent(idVK);

        xhr2.open("POST", 'user?fName=Чувак&lName=Чуваков&vkId='+idVK, false)
        xhr2.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded')
        xhr2.send(body);
        //alert(xhr2.responseText);
       var otvet2=JSON.parse(xhr2.responseText);
            UserID=otvet2.id;
            alert(UserID);


    }
    else{

        //alert("есть");

        var otvet;

        otvet = JSON.parse(x.responseText);

        UserID = otvet.id;
        alert(UserID);

    }


}
