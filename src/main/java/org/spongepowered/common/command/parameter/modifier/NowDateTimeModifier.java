/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.common.command.parameter.modifier;

import org.spongepowered.api.command.parameter.ArgumentParseException;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.parameter.managed.ParsingContext;
import org.spongepowered.api.command.parameter.managed.standard.CatalogedValueParameterModifier;
import org.spongepowered.api.command.parameter.token.CommandArgs;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;

import java.time.LocalDateTime;

public class NowDateTimeModifier implements CatalogedValueParameterModifier {

    @Override
    public String getId() {
        return "sponge:now";
    }

    @Override
    public String getName() {
        return "Now datetime modifier";
    }

    @Override
    public void onParse(Text key, Cause cause, CommandArgs args, CommandContext context, ParsingContext parsingContext)
            throws ArgumentParseException {
        try {
            parsingContext.next();
            if (context.hasAny(key)) {
                return;
            }
        } catch (Exception e) {
            // ignored
        }

        context.putEntry(key, LocalDateTime.now());
    }
}