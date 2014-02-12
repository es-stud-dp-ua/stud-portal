function a() {
    jQuery('#cropbox').Jcrop({onChange: setCoords, onSelect: setCoords, bgColor: 'black',
        bgOpacity: .4,
        setSelect: [100, 0, 253, 353],
        aspectRatio: 1});
}