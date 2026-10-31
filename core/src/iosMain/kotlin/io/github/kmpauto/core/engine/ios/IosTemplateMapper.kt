package io.github.kmpauto.core.engine.ios

import io.github.kmpauto.core.components.AutoAction
import io.github.kmpauto.core.components.AutoGridItem
import io.github.kmpauto.core.components.AutoListItem
import io.github.kmpauto.core.components.AutoListSection
import io.github.kmpauto.core.components.AutoPaneItem
import io.github.kmpauto.core.template.AutoGridTemplate
import io.github.kmpauto.core.template.AutoListTemplate
import io.github.kmpauto.core.template.AutoMessageTemplate
import io.github.kmpauto.core.template.AutoPaneTemplate
import io.github.kmpauto.core.template.AutoTemplate
import platform.CarPlay.CPAlertAction
import platform.CarPlay.CPAlertActionStyleDefault
import platform.CarPlay.CPAlertActionStyleDestructive
import platform.CarPlay.CPAlertTemplate
import platform.CarPlay.CPGridButton
import platform.CarPlay.CPGridTemplate
import platform.CarPlay.CPInformationItem
import platform.CarPlay.CPInformationTemplate
import platform.CarPlay.CPListItem
import platform.CarPlay.CPListSection
import platform.CarPlay.CPListTemplate
import platform.CarPlay.CPTemplate
import platform.CarPlay.CPTextButton
import platform.CarPlay.CPTextButtonStyleNormal
import platform.UIKit.UIImage

/**
 * Maps unified [AutoTemplate] models into native Apple CarPlay [CPTemplate] components.
 */
public object IosTemplateMapper {

    public fun toCarPlayTemplate(autoTemplate: AutoTemplate): CPTemplate {
        return when (autoTemplate) {
            is AutoListTemplate -> toListTemplate(autoTemplate)
            is AutoGridTemplate -> toGridTemplate(autoTemplate)
            is AutoPaneTemplate -> toInformationTemplate(autoTemplate)
            is AutoMessageTemplate -> toAlertTemplate(autoTemplate)
        }
    }

    public fun toListTemplate(template: AutoListTemplate): CPListTemplate {
        val cpSections = template.sections.map { section ->
            val cpItems = section.items.map { item ->
                val listItem = CPListItem(
                    text = item.title,
                    detailText = item.subtitle
                )
                item.onClick?.let { click ->
                    listItem.handler = { _, completion ->
                        click()
                        completion?.invoke()
                    }
                }
                listItem
            }
            CPListSection(items = cpItems, header = section.title, sectionIndexTitle = null)
        }

        return CPListTemplate(
            title = template.title,
            sections = cpSections
        )
    }

    public fun toGridTemplate(template: AutoGridTemplate): CPGridTemplate {
        val buttons = template.items.map { item ->
            val image = UIImage.systemImageNamed("car.fill") ?: UIImage()
            CPGridButton(
                titleVariants = listOf(item.title),
                image = image,
                handler = { _ -> item.onClick?.invoke() }
            )
        }

        return CPGridTemplate(
            title = template.title,
            gridButtons = buttons
        )
    }

    public fun toInformationTemplate(template: AutoPaneTemplate): CPInformationTemplate {
        val cpItems = template.items.map { item ->
            CPInformationItem(
                title = item.title,
                detail = item.text
            )
        }

        val cpButtons = template.actions.map { action ->
            when (action) {
                is AutoAction.Primary -> CPTextButton(
                    title = action.title,
                    textStyle = CPTextButtonStyleNormal,
                    handler = { _ -> action.onClick() }
                )
                is AutoAction.Standard -> CPTextButton(
                    title = action.title,
                    textStyle = CPTextButtonStyleNormal,
                    handler = { _ -> action.onClick() }
                )
                is AutoAction.Destructive -> CPTextButton(
                    title = action.title,
                    textStyle = CPTextButtonStyleNormal,
                    handler = { _ -> action.onClick() }
                )
                else -> CPTextButton(
                    title = "OK",
                    textStyle = CPTextButtonStyleNormal,
                    handler = {}
                )
            }
        }

        return CPInformationTemplate(
            title = template.title,
            layout = 0, // CPInformationTemplateLayoutLeading
            items = cpItems,
            actions = cpButtons
        )
    }

    public fun toAlertTemplate(template: AutoMessageTemplate): CPAlertTemplate {
        val cpActions = template.actions.map { action ->
            val title = when (action) {
                is AutoAction.Primary -> action.title
                is AutoAction.Standard -> action.title
                is AutoAction.Destructive -> action.title
                is AutoAction.Back -> "Back"
                is AutoAction.AppIcon -> "Close"
            }
            val style = if (action is AutoAction.Destructive) {
                CPAlertActionStyleDestructive
            } else {
                CPAlertActionStyleDefault
            }
            val onClick: (() -> Unit)? = when (action) {
                is AutoAction.Primary -> action.onClick
                is AutoAction.Standard -> action.onClick
                is AutoAction.Destructive -> action.onClick
                else -> null
            }

            CPAlertAction(
                title = title,
                style = style,
                handler = { _ -> onClick?.invoke() }
            )
        }

        return CPAlertTemplate(
            titleVariants = listOf(template.message),
            actions = cpActions
        )
    }
}
