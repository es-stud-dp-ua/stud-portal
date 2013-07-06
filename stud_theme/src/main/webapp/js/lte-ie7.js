/* Load this script using conditional IE comments if you need to support IE 7 and IE 6. */

window.onload = function() {
	function addIcon(el, entity) {
		var html = el.innerHTML;
		el.innerHTML = '<span style="font-family: \'portlet-content-panel\'">' + entity + '</span>' + html;
	}
	var icons = {
			'icon-pcppencil' : '&#xe000;',
			'icon-pcpremove' : '&#xe001;',
			'icon-pcpundo' : '&#xe002;',
			'icon-pcpredo' : '&#xe003;',
			'icon-pcpplus' : '&#xe004;',
			'icon-pcpminus' : '&#xe005;',
			'icon-pcparrow-left' : '&#xe006;',
			'icon-pcparrow-right' : '&#xe007;',
			'icon-pcparrow-down' : '&#xe008;',
			'icon-pcparrow-up' : '&#xe009;',
			'icon-pcpfile' : '&#xe00a;'
		},
		els = document.getElementsByTagName('*'),
		i, attr, html, c, el;
	for (i = 0; ; i += 1) {
		el = els[i];
		if(!el) {
			break;
		}
		attr = el.getAttribute('data-icon');
		if (attr) {
			addIcon(el, attr);
		}
		c = el.className;
		c = c.match(/icon-pcp[^\s'"]+/);
		if (c && icons[c[0]]) {
			addIcon(el, icons[c[0]]);
		}
	}
};