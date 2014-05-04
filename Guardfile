guard :shell do
  watch(/.*\.adoc/) do |m|
    `asciidoctor -D out/ main.adoc` # html5
    `asciidoctor -b docbook5 -d book -D out/ main.adoc` #docbook

    # Generates the PDF
    # `fopub  -t $HOME/Documents/miw/tfm/doc/tfm-doc/out/docbook-xsl $HOME/Documents/miw/tfm/doc/tfm-doc/out/main.xml`
  end
end
