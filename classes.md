---
layout: page
title: Class index
permalink: /classes/
---

{% assign linesplit = "|||" %}
{% assign itemsplit = "###" %}

<!-- Iterate all posts and find Java import statments -->
{% capture classPostMap %} 
  {% for post in site.posts %}
    {% assign lines = post | newline_to_br | split: '<br />' %}
    {% for line in lines %}
      {% if line contains "rememberjava" %}
        {% continue %}
      {% endif %}
      {% if line contains "import" and line contains ";" %}
         {{linesplit}}{{ line | strip_newlines | replace: "import", "" | replace: "static", "" | replace: ";", "" | replace: ".*", "" | strip_html | replace: " ","" }}{{itemsplit}}{{ post.title }}{{itemsplit}}{{ post.url }}{{itemsplit}}{{ post.date }}<br>
      {% endif %}
    {% endfor %}
  {% endfor %}
{% endcapture %}

<!-- Sort by class name and post title, uniq. (The same post might have multiple included files,
     each with the same imported class.) -->
{% assign sortedClassPostMap = classPostMap | split: linesplit | sort | uniq %}

{% assign prevClass = nil %}
{% assign prevTitle = nil %}
{% assign prevUrl = nil %}
{% assign prevDate = nil %}

{% assign startBox = true %}
{% assign endBox = false %}

<div class="classIndex">

{% for line in sortedClassPostMap %}

  {% assign items = line | split: itemsplit %}
  
  {% assign class = items[0] %}
  {% assign title = items[1] %}
  {% assign url = items[2] %}
  {% assign date = items[3] %}

  <!-- Post link -->
  {% if title != prevTitle and prevTitle != nil  or forloop.last == true %}
    {% if startBox == false  %}
      <ul>
    {% endif %}
    <li>
    <span class="classPost">
      <a class="title" href="{{prevUrl}}">{{prevTitle}}</a>
      <span class="date">{{prevDate | date: "%Y-%m-%d" }}</span>
    </span>
    </li>
    
    {% assign startBox = true %}
  {% endif %}
  
  <!-- Class header -->
  {% if class != prevClass %}
    {% if startBox %}
      {% if forloop.first == false %}
        </ul></div>
      {% endif %}
      <div class="classBox">
      {% assign startBox = false %}
    {% endif %}
    
    {% assign classParts = class | split: "." %}
    <div class="classClass">
      <span class="package">{% for part in classParts %}{% if forloop.last == true %}</span><span class="name">{{part}}</span>{% else %}{{part}}.{% endif %}{% endfor %}
    </div>
  {% endif %}
  
  <!-- Last post special case -->
  {% if forloop.last == true %}
    <ul><li>
    <span class="classPost">
      <a class="title" href="{{prevUrl}}">{{prevTitle}}</a>
      <span class="date">{{prevDate | date: "%Y-%m-%d" }}</span>
    </span>
    </li></ul>
  {% endif%}
  
  {% assign prevClass = class %}
  {% assign prevTitle = title %}
  {% assign prevUrl = url %}
  {% assign prevDate = date %}

{% endfor %}

</div>
