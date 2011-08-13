<div class="field$if(!field.valid)$ error$endif$">
    <input type="text" id="$field.name$" name="$field.name$" value="$field.value$">
    $if(!field.valid)$<div class="$field.name$ message">$field.message$</div>$endif$
</div>
