/* Load this script using conditional IE comments if you need to support IE 7 and IE 6. */

window.onload = function () {
    function addIcon(el, entity) {
        var html = el.innerHTML;
        el.innerHTML = '<span style="font-family: \'icomoon\'">' + entity + '</span>' + html;
    }

    var icons = {
            'icon-bubbles': '&#xe000;',
            'icon-bubbles-2': '&#xe001;',
            'icon-bubbles-3': '&#xe002;',
            'icon-bubbles-4': '&#xe003;',
            'icon-earth': '&#xe004;',
            'icon-stackoverflow': '&#xe005;',
            'icon-office': '&#xe006;',
            'icon-share': '&#xe007;',
            'icon-thumbs-up': '&#xe008;',
            'icon-thumbs-up-2': '&#xe009;'
        },
        els = document.getElementsByTagName('*'),
        i, attr, html, c, el;
    for (i = 0; ; i += 1) {
        el = els[i];
        if (!el) {
            break;
        }
        attr = el.getAttribute('data-icon');
        if (attr) {
            addIcon(el, attr);
        }
        c = el.className;
        c = c.match(/icon-[^\s'"]+/);
        if (c && icons[c[0]]) {
            addIcon(el, icons[c[0]]);
        }
    }
};