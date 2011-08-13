<div class="field$if(!field.valid)$ error$endif$">
    <select id="$field.name$" name="$field.name$">
        $options:{ option | <option value="$option.value$"$if(option.selected)$ selected$endif$>$option.value$</option>}$
    </select>
    $if(!field.valid)$<div class="$field.name$ message">$field.message$</div>$endif$
</div>
