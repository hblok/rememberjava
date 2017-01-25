cp Gemfile.local Gemfile
rm Gemfile.lock
rm ./_site/assets/main.css
jekyll s --unpublished --baseurl ''
