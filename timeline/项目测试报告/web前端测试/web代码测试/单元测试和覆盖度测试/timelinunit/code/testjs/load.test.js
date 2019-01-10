QUnit.test( "showHTML test", function( assert ) {
    assert.equal(showHTML("true"),1,"showHTML(true)");
    assert.equal(showHTML("false"),2,"showHTML(false)");
    assert.equal(showHTML("%6kd89432"),0,"showHTML(other)");
});
QUnit.test( "showMore test", function( assert ) {
    assert.equal(showMore(0),1,"showMore(0)");
    assert.equal(showMore(3),2,"showMore(>0)");
    assert.equal(showMore(-5),0,"showMore(<0)");
});