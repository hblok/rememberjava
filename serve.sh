#cp Gemfile.local Gemfile
rm Gemfile.lock

jekyll s --force_polling --unpublished --baseurl ''
