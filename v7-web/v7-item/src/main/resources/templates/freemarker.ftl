<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    hello, ${username}
    <hr />
    name: ${product.name}
    price: ${product.price}
    createTime: ${product.createTime?date}
    createTime: ${product.createTime?time}
    createTime: ${product.createTime?datetime}
    <hr />
</body>
</html>