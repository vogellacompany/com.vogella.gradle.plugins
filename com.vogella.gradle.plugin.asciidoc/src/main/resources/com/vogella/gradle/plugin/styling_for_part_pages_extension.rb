require 'asciidoctor-pdf' unless defined? ::Asciidoctor::Pdf

class Asciidoctor::Pdf::ThemeLoader
  old_process_entry = instance_method(:process_entry)

  define_method(:process_enty) do |key, val, data|
    if ::Hash === val
      data[key + '?'] = true
    end

    old_process_entry.bind(self).(key, val, data)
  end
end

module StylingForPartPagesExtension

  def layout_chapter_title node, title, opts = {}
    if (node.level > 0) || !(page_prefix = page_prefix_for node.id)
      layout_heading title, opts
      return
    end

    doc = node.document
    prev_bg_image = @page_bg_image
    prev_bg_color = @page_bg_color

    if (bg_image = resolve_background_image doc, @theme, 'chapter-page-background-image')
      @page_bg_image = (bg_image == 'none' ? nil : bg_image)
    end
    if (bg_color = resolve_theme_color :chapter_page_background_color)
      @page_bg_color = bg_color
    end
    # NOTE a new page will already be started if the cover image is a PDF
    start_new_page unless page_is_empty?
    start_new_page if @ppbook && verso_page?
    @page_bg_image = prev_bg_image if bg_image
    @page_bg_color = prev_bg_color if bg_color

    # IMPORTANT this is the first page created, so we need to set the base font
    font @theme.base_font_family, size: @theme.base_font_size

    # QUESTION allow alignment per element on chapter page?
    chapter_align = (@theme.send("#{page_prefix}_page_align") || @base_align).to_sym

    # TODO disallow .pdf as image type
    if (logo_image_path = (doc.attr "#{page_prefix}-logo-image", @theme.send("#{page_prefix}_page_logo_image")))
      if (logo_image_path.include? ':') && logo_image_path =~ ImageAttributeValueRx
        logo_image_path = $1
        logo_image_attrs = (AttributeList.new $2).parse ['alt', 'width', 'height']
        relative_to_imagesdir = true
      else
        logo_image_attrs = {}
        relative_to_imagesdir = false
      end
      # HACK quick fix to resolve image path relative to theme
      unless doc.attr? "#{page_prefix}-logo-image"
        logo_image_path = ThemeLoader.resolve_theme_asset logo_image_path, (doc.attr 'pdf-stylesdir')
      end
      logo_image_attrs['target'] = logo_image_path
      logo_image_attrs['align'] ||= (@theme.send("#{page_prefix}_page_logo_align") || chapter_align.to_s)
      # QUESTION should we allow theme to turn logo image off?
      logo_image_top = logo_image_attrs['top'] || @theme.send("#{page_prefix}_page_logo_top") || '10%'
      # FIXME delegate to method to convert page % to y value
      if logo_image_top.end_with? 'vh'
        logo_image_top = page_height - page_height * logo_image_top.to_f / 100.0
      else
        logo_image_top = bounds.absolute_top - effective_page_height * logo_image_top.to_f / 100.0
      end
      initial_y, @y = @y, logo_image_top
      # FIXME add API to Asciidoctor for creating blocks like this (extract from extensions module?)
      image_block = ::Asciidoctor::Block.new doc, :image, content_model: :empty, attributes: logo_image_attrs
      # NOTE pinned option keeps image on same page
      convert_image image_block, relative_to_imagesdir: relative_to_imagesdir, pinned: true
      @y = initial_y
    end

    # TODO prevent content from spilling to next page
    theme_font "#{page_prefix}_page".to_sym do
      if (chapter_top = @theme.send("#{page_prefix}_page_title_top"))
        if chapter_top.end_with? 'vh'
          chapter_top = page_height - page_height * chapter_top.to_f / 100.0
        else
          chapter_top = bounds.absolute_top - effective_page_height * chapter_top.to_f / 100.0
        end
        # FIXME delegate to method to convert page % to y value
        @y = chapter_top
      end
      move_down (@theme.send("#{page_prefix}_page_title_margin_top") || 0)
      theme_font "#{page_prefix}_page_title".to_sym do
        layout_heading title,
                       align: chapter_align,
                       margin: 0,
                       line_height: @theme.send("#{page_prefix}_page_title_line_height")
      end
      move_down (@theme.send("#{page_prefix}_page_title_margin_bottom") || 0)
    end
  end

  def page_prefix_for sect_id
    sections = ['index', 'appendix']
    page_prefix = sections.select { |section_title| section_title == sect_id }[0]
    return page_prefix if defined_in_theme? page_prefix
    page_prefix = 'chapter'
    return page_prefix if defined_in_theme? page_prefix
  end

  def defined_in_theme? sect_id
    @theme.send("#{sect_id}_page?")
  end
end

#Asciidoctor::Pdf::ThemeLoader.prepend ThemeLoaderExtension
Asciidoctor::Pdf::Converter.prepend StylingForPartPagesExtension
