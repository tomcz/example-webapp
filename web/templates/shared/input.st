$if(field.valid)$
<div class="field">
    <input type="text" id="$field.name$" name="$field.name$" value="$field.value$" />
</div>
$else$
<div class="field error">
    <input type="text" id="$field.name$" name="$field.name$" value="$field.value$" />
    <br />
    <span class="$field.name$ message">$field.message$</span>
</div>
$endif$
