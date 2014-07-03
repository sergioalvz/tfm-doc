guard :shell do
  watch(/.*\.[adoc]/) do |m|
    `bundle exec asciidoctor -a source-highlighter=pygments -a pygments-css=inline -D out/ main.adoc` # html5
    `bundle exec asciidoctor -b docbook5 -d book -D out/ main.adoc` #docbook
  end
end
