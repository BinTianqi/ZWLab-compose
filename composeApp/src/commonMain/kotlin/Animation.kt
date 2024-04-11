import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

val animateAlpha: FiniteAnimationSpec<Float> = spring(stiffness = Spring.StiffnessLow)
