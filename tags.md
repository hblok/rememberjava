---
layout: page
title: Tag index
permalink: /tags/
---

<div class="tagIndex">

{% capture tags %}{% for tag in site.categories %}{{tag[0]}}{{','}}{% endfor %}{% endcapture %}
{% assign sortedtags = tags | downcase | split:"," | sort %}

{% for tag in sortedtags %}
  {% for t in site.categories %}
    {% assign cmpt = t[0] | downcase %}
    {% if cmpt == tag %}

      <div class="tagBox">
	<h2 id="{{ tag }}-ref">{{ tag }}</h2>
	<ul class="unstyled">
	{% for post in t[1] %}
	  <li><a class="tagLink" href="{{post.url}}"><i class="icon-file"></i> {{post.title}}</a> - <span class="tagDate">{{post.date | date: "%Y-%m-%d" }}</span></li>
	{% endfor %}
	</ul>
      </div>

    {% endif %}
  {% endfor %}
{% endfor %}

</div>
