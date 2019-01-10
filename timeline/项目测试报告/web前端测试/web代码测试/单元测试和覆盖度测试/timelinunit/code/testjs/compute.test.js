QUnit.test( "add()测试", function( assert ) {
    assert.equal(add(1,2),true,"add(1,2)");
});
QUnit.test( "reduc()测试", function( assert ) {
    assert.equal(reduc(1,2),false,"reduc(1,2)");
    assert.equal(reduc(2,1),true,"reduc(1,2)");
});