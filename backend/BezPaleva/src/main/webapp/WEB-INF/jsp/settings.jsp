

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Настройки</title>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
</head>
<body>

    <form action="/admin" method="post" style="display:grid">


        <label>Метка удаляется через <input class="num" type="text" name="deathTimeSize" value="${systemParam.deathTimeSize}"/> часов</label>
        <label>Максимальный уровень неактуальности метки <input class="num" type="text" name="irrelevanceLevelMax" value="${systemParam.irrelevanceLevelMax}"/></label>
        <label>Максимальное количество меток пользотателя в сутки <input class="num" type="text" name="maxNumberMarksPerDay" value="${systemParam.maxNumberMarksPerDay}"/></label>
        <label>Секретный ключ <input type="password" name="secretPass"/></label>
        <input type="submit" value="Изменить настройки" style="display: contents"/>
    </form>

<script>
    $('.num').bind("change keyup input click", function () {

        if (this.value.match(/[^0-9]/g)) {
            this.value = this.value.replace(/[^0-9]/g, '');
        }

        if(this.value <0 || this.value >= 10000){
            this.value = 1;
        }

    });
</script>
</body>
</html>
